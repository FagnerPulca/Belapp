package br.com.belapp.belapp.model;



import java.io.Serializable;

public class Estabelecimento implements Serializable {

    private String mEid;
    private String mNome;
    private String mDescricao;
    private String mIdEndereco;
    private double mDistancia;
    private double mLatitude;
    private double mLongitude;
    private String mRua;
    private String mNumero;
    private String mBairro;
    private String mCidade;
    private String mComplemento;
    private String mCep;
    private String img;

    public Estabelecimento(){

    }

    public Estabelecimento(String mEid, String mNome, String mDescricao, double mDistancia, String img) {
        this.mEid = mEid;
        this.mNome = mNome;
        this.mDescricao = mDescricao;
        this.mIdEndereco = mIdEndereco;
        this.mDistancia = mDistancia;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getmEid() {
        return mEid;
    }

    public void setmEid(String mEid) {
        this.mEid = mEid;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }

    public String getmDescricao() {
        return mDescricao;
    }

    public void setmDescricao(String mDescricao) {
        this.mDescricao = mDescricao;
    }

    public String getmIdEndereco() {
        return mIdEndereco;
    }

    public void setmIdEndereco(String mIdEndereco) {
        this.mIdEndereco = mIdEndereco;
    }

    public double getmDistancia() {
        return mDistancia;
    }

    public void setmDistancia(double mDistancia) {
        this.mDistancia = mDistancia;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmRua() {
        return mRua;
    }

    public void setmRua(String mRua) {
        this.mRua = mRua;
    }

    public String getmNumero() {
        return mNumero;
    }

    public void setmNumero(String mNumero) {
        this.mNumero = mNumero;
    }

    public String getmBairro() {
        return mBairro;
    }

    public void setmBairro(String mBairro) {
        this.mBairro = mBairro;
    }

    public String getmCidade() {
        return mCidade;
    }

    public void setmCidade(String mCidade) {
        this.mCidade = mCidade;
    }

    public String getmComplemento() {
        return mComplemento;
    }

    public void setmComplemento(String mComplemento) {
        this.mComplemento = mComplemento;
    }

    public String getmCep() {
        return mCep;
    }

    public void setmCep(String mCep) {
        this.mCep = mCep;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Estabelecimento && this.mEid == ((Estabelecimento) obj).mEid);
    }
    //Aqui atributos e metodos da classe
    /*String mEId;
    String mNome;
    String mTelefone;
    String mDescricao;
    String mMediaAvaliacoes;
    String mGaleria;
    Endereco mEndereco_ID;
    String mLocalizacao;
    double mLaititude;
    double mLongitude;
    ArrayList<Servico> mServicos;
    ArrayList<Promocoes> mPromocoes;
    ArrayList<Profissional> mProfissionais;
    ArrayList<String> mAvaliacoes;
    private double distancia;
    private DatabaseReference mDataBase;

    public Estabelecimento() {
    }


    public Estabelecimento(String mDescricao, String mEId, String mGaleria, String mLocalizacao, String mMediaAvaliacoes,
                           String mNome, String mTelefone){

        this.mDescricao = mDescricao;
        this.mGaleria = mGaleria;
        this.mLocalizacao = mLocalizacao;
        this.mMediaAvaliacoes = mMediaAvaliacoes;
        this.mNome = mNome;
        this.mTelefone = mTelefone;

    }

    public Estabelecimento(String mNome, String mTelefone, String mDescricao, String mMediaAvaliacoes,
                           String mGaleria, Endereco mEndereco, String mLocalizacao, double mLaititude, double mLongitude,
                           ArrayList<Servico> mServicos, ArrayList<Agenda> mAgenda,
                           ArrayList<Promocoes> mPromocoes, ArrayList<Profissional> mProfissionais, ArrayList<String> mAvaliacoes, double distancia) {
        this.mEId = mEId;
        this.mNome = mNome;
        this.mTelefone = mTelefone;
        this.mDescricao = mDescricao;
        this.mMediaAvaliacoes = mMediaAvaliacoes;
        this.mGaleria = mGaleria;
        this.mEndereco_ID = mEndereco;
        this.mLocalizacao = mLocalizacao;
        this.mLaititude = mLaititude;
        this.mLongitude = mLongitude;
        this.mServicos = mServicos;
        this.mAgenda = mAgenda;
        this.mPromocoes = mPromocoes;
        this.mProfissionais = mProfissionais;
        this.mAvaliacoes = mAvaliacoes;
        this.distancia = distancia;
    }

    public void addEstabelecimento(String nome, String telefone, String descricao, String mediaAvaliacoes,
                                   String galeria, String endereco, String localizacao, double latitude, double longitude, ArrayList<Servico> servicos_arl, ArrayList<Agenda> agenda_arl,
                                   ArrayList<Promocoes> promocoes_arl, ArrayList<Profissional> profissionais_arl, ArrayList<String> avaliacoes_arl) {

        Estabelecimento estabelecimento = new Estabelecimento(nome, telefone, descricao, mediaAvaliacoes,
                galeria, endereco, localizacao, latitude, longitude, servicos_arl, agenda_arl, promocoes_arl, profissionais_arl, avaliacoes_arl);

        mDataBase = FirebaseDatabase.getInstance().getReference();
        mDataBase.child("estabelecimentos").push().setValue(estabelecimento);
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getmEId() {
        return mEId;
    }

    public void setmEId(String mEId) {
        this.mEId = mEId;
    }

    public String getmNome() {
        return mNome;
    }

    public void setmNome(String mNome) {
        this.mNome = mNome;
    }

    public String getmTelefone() {
        return mTelefone;
    }

    public void setmTelefone(String mTelefone) {
        this.mTelefone = mTelefone;
    }

    public String getmDescricao() {
        return mDescricao;
    }

    public void setmDescricao(String mDescricao) {
        this.mDescricao = mDescricao;
    }

    public String getmMediaAvaliacoes() {
        return mMediaAvaliacoes;
    }

    public void setmMediaAvaliacoes(String mMediaAvaliacoes) {
        this.mMediaAvaliacoes = mMediaAvaliacoes;
    }

    public String getmGaleria() {
        return mGaleria;
    }

    public void setmGaleria(String mGaleria) {
        this.mGaleria = mGaleria;
    }

    public Endereco getmEndereco_ID() {
        return mEndereco_ID;
    }

    public void setmEndereco(Endereco mEndereco) {
        this.mEndereco_ID = mEndereco;
    }

    public String getmLocalizacao() {
        return mLocalizacao;
    }

    public void setmLocalizacao(String mLocalizacao) {
        this.mLocalizacao = mLocalizacao;
    }

    public double getmLaititude() {
        return mLaititude;
    }

    public void setmLaititude(double mLaititude) {
        this.mLaititude = mLaititude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public ArrayList<Servico> getmServicos() {
        return mServicos;
    }

    public void setmServicos(ArrayList<Servico> mServicos) {
        this.mServicos = mServicos;
    }

    public ArrayList<Agenda> getmAgenda() {
        return mAgenda;
    }

    public void setmAgenda(ArrayList<Agenda> mAgenda) {
        this.mAgenda = mAgenda;
    }

    public ArrayList<Promocoes> getmPromocoes() {
        return mPromocoes;
    }

    public void setmPromocoes(ArrayList<Promocoes> mPromocoes) {
        this.mPromocoes = mPromocoes;
    }

    public ArrayList<Profissional> getmProfissionais() {
        return mProfissionais;
    }

    public void setmProfissionais(ArrayList<Profissional> mProfissionais) {
        this.mProfissionais = mProfissionais;
    }

    public ArrayList<String> getmAvaliacoes() {
        return mAvaliacoes;
    }

    public void setmAvaliacoes(ArrayList<String> mAvaliacoes) {
        this.mAvaliacoes = mAvaliacoes;
    }

    public void setmDataBase(DatabaseReference mDataBase) {
        this.mDataBase = mDataBase;
    }*/

}
